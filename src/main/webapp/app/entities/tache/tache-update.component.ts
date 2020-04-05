import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITache, Tache } from 'app/shared/model/tache.model';
import { TacheService } from './tache.service';
import { IDegreImportance } from 'app/shared/model/degre-importance.model';
import { DegreImportanceService } from 'app/entities/degre-importance/degre-importance.service';
import { IDegreUrgence } from 'app/shared/model/degre-urgence.model';
import { DegreUrgenceService } from 'app/entities/degre-urgence/degre-urgence.service';
import { IMatrice } from 'app/shared/model/matrice.model';
import { MatriceService } from 'app/entities/matrice/matrice.service';
import { ICategorieTache } from 'app/shared/model/categorie-tache.model';
import { CategorieTacheService } from 'app/entities/categorie-tache/categorie-tache.service';

type SelectableEntity = IDegreImportance | IDegreUrgence | IMatrice | ICategorieTache;

@Component({
  selector: 'jhi-tache-update',
  templateUrl: './tache-update.component.html'
})
export class TacheUpdateComponent implements OnInit {
  isSaving = false;
  degreimportances: IDegreImportance[] = [];
  degreurgences: IDegreUrgence[] = [];
  matrices: IMatrice[] = [];
  categorietaches: ICategorieTache[] = [];
  dateIdealeDp: any;
  dateAlerteDp: any;
  dateDelaiDp: any;

  editForm = this.fb.group({
    id: [],
    libelle: [null, [Validators.required]],
    descritpion: [],
    dateIdeale: [],
    dateAlerte: [],
    dateDelai: [],
    degreImportance: [],
    degreUrgence: [],
    matrice: [],
    categorie: []
  });

  constructor(
    protected tacheService: TacheService,
    protected degreImportanceService: DegreImportanceService,
    protected degreUrgenceService: DegreUrgenceService,
    protected matriceService: MatriceService,
    protected categorieTacheService: CategorieTacheService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tache }) => {
      this.updateForm(tache);

      this.degreImportanceService.query().subscribe((res: HttpResponse<IDegreImportance[]>) => (this.degreimportances = res.body || []));

      this.degreUrgenceService.query().subscribe((res: HttpResponse<IDegreUrgence[]>) => (this.degreurgences = res.body || []));

      this.matriceService.query().subscribe((res: HttpResponse<IMatrice[]>) => (this.matrices = res.body || []));

      this.categorieTacheService.query().subscribe((res: HttpResponse<ICategorieTache[]>) => (this.categorietaches = res.body || []));
    });
  }

  updateForm(tache: ITache): void {
    this.editForm.patchValue({
      id: tache.id,
      libelle: tache.libelle,
      descritpion: tache.descritpion,
      dateIdeale: tache.dateIdeale,
      dateAlerte: tache.dateAlerte,
      dateDelai: tache.dateDelai,
      degreImportance: tache.degreImportance,
      degreUrgence: tache.degreUrgence,
      matrice: tache.matrice,
      categorie: tache.categorie
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tache = this.createFromForm();
    if (tache.id !== undefined) {
      this.subscribeToSaveResponse(this.tacheService.update(tache));
    } else {
      this.subscribeToSaveResponse(this.tacheService.create(tache));
    }
  }

  private createFromForm(): ITache {
    return {
      ...new Tache(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      descritpion: this.editForm.get(['descritpion'])!.value,
      dateIdeale: this.editForm.get(['dateIdeale'])!.value,
      dateAlerte: this.editForm.get(['dateAlerte'])!.value,
      dateDelai: this.editForm.get(['dateDelai'])!.value,
      degreImportance: this.editForm.get(['degreImportance'])!.value,
      degreUrgence: this.editForm.get(['degreUrgence'])!.value,
      matrice: this.editForm.get(['matrice'])!.value,
      categorie: this.editForm.get(['categorie'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITache>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
