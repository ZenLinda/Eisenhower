import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMatrice } from 'app/shared/model/matrice.model';
import { MatriceService } from './matrice.service';

@Component({
  templateUrl: './matrice-delete-dialog.component.html'
})
export class MatriceDeleteDialogComponent {
  matrice?: IMatrice;

  constructor(protected matriceService: MatriceService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.matriceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('matriceListModification');
      this.activeModal.close();
    });
  }
}
