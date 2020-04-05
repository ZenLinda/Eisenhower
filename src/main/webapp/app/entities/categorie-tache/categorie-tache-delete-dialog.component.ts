import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICategorieTache } from 'app/shared/model/categorie-tache.model';
import { CategorieTacheService } from './categorie-tache.service';

@Component({
  templateUrl: './categorie-tache-delete-dialog.component.html'
})
export class CategorieTacheDeleteDialogComponent {
  categorieTache?: ICategorieTache;

  constructor(
    protected categorieTacheService: CategorieTacheService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.categorieTacheService.delete(id).subscribe(() => {
      this.eventManager.broadcast('categorieTacheListModification');
      this.activeModal.close();
    });
  }
}
