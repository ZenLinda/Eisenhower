import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICategorieTache, CategorieTache } from 'app/shared/model/categorie-tache.model';
import { CategorieTacheService } from './categorie-tache.service';

@Component({
  selector: 'jhi-categorie-tache-update',
  templateUrl: './categorie-tache-update.component.html'
})
export class CategorieTacheUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    libelle: [null, [Validators.required]],
    couleur: [null, [Validators.required]]
  });

  constructor(protected categorieTacheService: CategorieTacheService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categorieTache }) => {
      this.updateForm(categorieTache);
    });
  }

  updateForm(categorieTache: ICategorieTache): void {
    this.editForm.patchValue({
      id: categorieTache.id,
      libelle: categorieTache.libelle,
      couleur: categorieTache.couleur
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const categorieTache = this.createFromForm();
    if (categorieTache.id !== undefined) {
      this.subscribeToSaveResponse(this.categorieTacheService.update(categorieTache));
    } else {
      this.subscribeToSaveResponse(this.categorieTacheService.create(categorieTache));
    }
  }

  private createFromForm(): ICategorieTache {
    return {
      ...new CategorieTache(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      couleur: this.editForm.get(['couleur'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategorieTache>>): void {
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
