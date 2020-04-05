import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDegreImportance } from 'app/shared/model/degre-importance.model';
import { DegreImportanceService } from './degre-importance.service';

@Component({
  templateUrl: './degre-importance-delete-dialog.component.html'
})
export class DegreImportanceDeleteDialogComponent {
  degreImportance?: IDegreImportance;

  constructor(
    protected degreImportanceService: DegreImportanceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.degreImportanceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('degreImportanceListModification');
      this.activeModal.close();
    });
  }
}
