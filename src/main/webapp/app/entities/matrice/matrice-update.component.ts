import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMatrice, Matrice } from 'app/shared/model/matrice.model';
import { MatriceService } from './matrice.service';
import { IUtilisateur } from 'app/shared/model/utilisateur.model';
import { UtilisateurService } from 'app/entities/utilisateur/utilisateur.service';

@Component({
  selector: 'jhi-matrice-update',
  templateUrl: './matrice-update.component.html'
})
export class MatriceUpdateComponent implements OnInit {
  isSaving = false;
  utilisateurs: IUtilisateur[] = [];

  editForm = this.fb.group({
    id: [],
    libelle: [null, [Validators.required]],
    utilisateur: []
  });

  constructor(
    protected matriceService: MatriceService,
    protected utilisateurService: UtilisateurService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ matrice }) => {
      this.updateForm(matrice);

      this.utilisateurService.query().subscribe((res: HttpResponse<IUtilisateur[]>) => (this.utilisateurs = res.body || []));
    });
  }

  updateForm(matrice: IMatrice): void {
    this.editForm.patchValue({
      id: matrice.id,
      libelle: matrice.libelle,
      utilisateur: matrice.utilisateur
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const matrice = this.createFromForm();
    if (matrice.id !== undefined) {
      this.subscribeToSaveResponse(this.matriceService.update(matrice));
    } else {
      this.subscribeToSaveResponse(this.matriceService.create(matrice));
    }
  }

  private createFromForm(): IMatrice {
    return {
      ...new Matrice(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      utilisateur: this.editForm.get(['utilisateur'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMatrice>>): void {
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

  trackById(index: number, item: IUtilisateur): any {
    return item.id;
  }
}
