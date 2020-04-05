import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICategorieTache } from 'app/shared/model/categorie-tache.model';

@Component({
  selector: 'jhi-categorie-tache-detail',
  templateUrl: './categorie-tache-detail.component.html'
})
export class CategorieTacheDetailComponent implements OnInit {
  categorieTache: ICategorieTache | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categorieTache }) => (this.categorieTache = categorieTache));
  }

  previousState(): void {
    window.history.back();
  }
}
