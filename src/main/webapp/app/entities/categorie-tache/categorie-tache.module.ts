import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EisenhowerSharedModule } from 'app/shared/shared.module';
import { CategorieTacheComponent } from './categorie-tache.component';
import { CategorieTacheDetailComponent } from './categorie-tache-detail.component';
import { CategorieTacheUpdateComponent } from './categorie-tache-update.component';
import { CategorieTacheDeleteDialogComponent } from './categorie-tache-delete-dialog.component';
import { categorieTacheRoute } from './categorie-tache.route';

@NgModule({
  imports: [EisenhowerSharedModule, RouterModule.forChild(categorieTacheRoute)],
  declarations: [
    CategorieTacheComponent,
    CategorieTacheDetailComponent,
    CategorieTacheUpdateComponent,
    CategorieTacheDeleteDialogComponent
  ],
  entryComponents: [CategorieTacheDeleteDialogComponent]
})
export class EisenhowerCategorieTacheModule {}
