import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDegreImportance, DegreImportance } from 'app/shared/model/degre-importance.model';
import { DegreImportanceService } from './degre-importance.service';

@Component({
  selector: 'jhi-degre-importance-update',
  templateUrl: './degre-importance-update.component.html'
})
export class DegreImportanceUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    ordre: [null, [Validators.required]],
    libelle: [null, [Validators.required]]
  });

  constructor(
    protected degreImportanceService: DegreImportanceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ degreImportance }) => {
      this.updateForm(degreImportance);
    });
  }

  updateForm(degreImportance: IDegreImportance): void {
    this.editForm.patchValue({
      id: degreImportance.id,
      ordre: degreImportance.ordre,
      libelle: degreImportance.libelle
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const degreImportance = this.createFromForm();
    if (degreImportance.id !== undefined) {
      this.subscribeToSaveResponse(this.degreImportanceService.update(degreImportance));
    } else {
      this.subscribeToSaveResponse(this.degreImportanceService.create(degreImportance));
    }
  }

  private createFromForm(): IDegreImportance {
    return {
      ...new DegreImportance(),
      id: this.editForm.get(['id'])!.value,
      ordre: this.editForm.get(['ordre'])!.value,
      libelle: this.editForm.get(['libelle'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDegreImportance>>): void {
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
