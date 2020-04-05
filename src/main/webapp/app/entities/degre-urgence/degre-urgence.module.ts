import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EisenhowerSharedModule } from 'app/shared/shared.module';
import { DegreUrgenceComponent } from './degre-urgence.component';
import { DegreUrgenceDetailComponent } from './degre-urgence-detail.component';
import { DegreUrgenceUpdateComponent } from './degre-urgence-update.component';
import { DegreUrgenceDeleteDialogComponent } from './degre-urgence-delete-dialog.component';
import { degreUrgenceRoute } from './degre-urgence.route';

@NgModule({
  imports: [EisenhowerSharedModule, RouterModule.forChild(degreUrgenceRoute)],
  declarations: [DegreUrgenceComponent, DegreUrgenceDetailComponent, DegreUrgenceUpdateComponent, DegreUrgenceDeleteDialogComponent],
  entryComponents: [DegreUrgenceDeleteDialogComponent]
})
export class EisenhowerDegreUrgenceModule {}
