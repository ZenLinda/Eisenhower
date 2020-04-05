import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDegreUrgence, DegreUrgence } from 'app/shared/model/degre-urgence.model';
import { DegreUrgenceService } from './degre-urgence.service';

@Component({
  selector: 'jhi-degre-urgence-update',
  templateUrl: './degre-urgence-update.component.html'
})
export class DegreUrgenceUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    ordre: [null, [Validators.required]],
    libelle: [null, [Validators.required]]
  });

  constructor(protected degreUrgenceService: DegreUrgenceService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ degreUrgence }) => {
      this.updateForm(degreUrgence);
    });
  }

  updateForm(degreUrgence: IDegreUrgence): void {
    this.editForm.patchValue({
      id: degreUrgence.id,
      ordre: degreUrgence.ordre,
      libelle: degreUrgence.libelle
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const degreUrgence = this.createFromForm();
    if (degreUrgence.id !== undefined) {
      this.subscribeToSaveResponse(this.degreUrgenceService.update(degreUrgence));
    } else {
      this.subscribeToSaveResponse(this.degreUrgenceService.create(degreUrgence));
    }
  }

  private createFromForm(): IDegreUrgence {
    return {
      ...new DegreUrgence(),
      id: this.editForm.get(['id'])!.value,
      ordre: this.editForm.get(['ordre'])!.value,
      libelle: this.editForm.get(['libelle'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDegreUrgence>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
