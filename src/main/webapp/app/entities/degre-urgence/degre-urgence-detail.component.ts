import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDegreUrgence } from 'app/shared/model/degre-urgence.model';

@Component({
  selector: 'jhi-degre-urgence-detail',
  templateUrl: './degre-urgence-detail.component.html'
})
export class DegreUrgenceDetailComponent implements OnInit {
  degreUrgence: IDegreUrgence | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ degreUrgence }) => (this.degreUrgence = degreUrgence));
  }

  previousState(): void {
    window.history.back();
  }
}
