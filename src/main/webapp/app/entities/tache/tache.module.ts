import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EisenhowerSharedModule } from 'app/shared/shared.module';
import { TacheComponent } from './tache.component';
import { TacheDetailComponent } from './tache-detail.component';
import { TacheUpdateComponent } from './tache-update.component';
import { TacheDeleteDialogComponent } from './tache-delete-dialog.component';
import { tacheRoute } from './tache.route';

@NgModule({
  imports: [EisenhowerSharedModule, RouterModule.forChild(tacheRoute)],
  declarations: [TacheComponent, TacheDetailComponent, TacheUpdateComponent, TacheDeleteDialogComponent],
  entryComponents: [TacheDeleteDialogComponent]
})
export class EisenhowerTacheModule {}
