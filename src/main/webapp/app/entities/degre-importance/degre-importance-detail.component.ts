import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDegreImportance } from 'app/shared/model/degre-importance.model';

@Component({
  selector: 'jhi-degre-importance-detail',
  templateUrl: './degre-importance-detail.component.html'
})
export class DegreImportanceDetailComponent implements OnInit {
  degreImportance: IDegreImportance | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ degreImportance }) => (this.degreImportance = degreImportance));
  }

  previousState(): void {
    window.history.back();
  }
}
