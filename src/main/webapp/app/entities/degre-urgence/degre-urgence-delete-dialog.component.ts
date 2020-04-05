import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDegreUrgence } from 'app/shared/model/degre-urgence.model';
import { DegreUrgenceService } from './degre-urgence.service';

@Component({
  templateUrl: './degre-urgence-delete-dialog.component.html'
})
export class DegreUrgenceDeleteDialogComponent {
  degreUrgence?: IDegreUrgence;

  constructor(
    protected degreUrgenceService: DegreUrgenceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.degreUrgenceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('degreUrgenceListModification');
      this.activeModal.close();
    });
  }
}
