import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EisenhowerSharedModule } from 'app/shared/shared.module';
import { DegreImportanceComponent } from './degre-importance.component';
import { DegreImportanceDetailComponent } from './degre-importance-detail.component';
import { DegreImportanceUpdateComponent } from './degre-importance-update.component';
import { DegreImportanceDeleteDialogComponent } from './degre-importance-delete-dialog.component';
import { degreImportanceRoute } from './degre-importance.route';

@NgModule({
  imports: [EisenhowerSharedModule, RouterModule.forChild(degreImportanceRoute)],
  declarations: [
    DegreImportanceComponent,
    DegreImportanceDetailComponent,
    DegreImportanceUpdateComponent,
    DegreImportanceDeleteDialogComponent
  ],
  entryComponents: [DegreImportanceDeleteDialogComponent]
})
export class EisenhowerDegreImportanceModule {}
