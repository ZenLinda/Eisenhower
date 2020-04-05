import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EisenhowerSharedModule } from 'app/shared/shared.module';
import { MatriceComponent } from './matrice.component';
import { MatriceDetailComponent } from './matrice-detail.component';
import { MatriceUpdateComponent } from './matrice-update.component';
import { MatriceDeleteDialogComponent } from './matrice-delete-dialog.component';
import { matriceRoute } from './matrice.route';

@NgModule({
  imports: [EisenhowerSharedModule, RouterModule.forChild(matriceRoute)],
  declarations: [MatriceComponent, MatriceDetailComponent, MatriceUpdateComponent, MatriceDeleteDialogComponent],
  entryComponents: [MatriceDeleteDialogComponent]
})
export class EisenhowerMatriceModule {}
