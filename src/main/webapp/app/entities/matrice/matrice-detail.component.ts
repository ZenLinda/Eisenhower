import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMatrice } from 'app/shared/model/matrice.model';

@Component({
  selector: 'jhi-matrice-detail',
  templateUrl: './matrice-detail.component.html'
})
export class MatriceDetailComponent implements OnInit {
  matrice: IMatrice | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ matrice }) => (this.matrice = matrice));
  }

  previousState(): void {
    window.history.back();
  }
}
