import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { EisenhowerSharedModule } from 'app/shared/shared.module';
import { EisenhowerCoreModule } from 'app/core/core.module';
import { EisenhowerAppRoutingModule } from './app-routing.module';
import { EisenhowerHomeModule } from './home/home.module';
import { EisenhowerEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    EisenhowerSharedModule,
    EisenhowerCoreModule,
    EisenhowerHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    EisenhowerEntityModule,
    EisenhowerAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent]
})
export class EisenhowerAppModule {}
