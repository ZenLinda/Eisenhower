import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EisenhowerTestModule } from '../../../test.module';
import { CategorieTacheDetailComponent } from 'app/entities/categorie-tache/categorie-tache-detail.component';
import { CategorieTache } from 'app/shared/model/categorie-tache.model';

describe('Component Tests', () => {
  describe('CategorieTache Management Detail Component', () => {
    let comp: CategorieTacheDetailComponent;
    let fixture: ComponentFixture<CategorieTacheDetailComponent>;
    const route = ({ data: of({ categorieTache: new CategorieTache(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EisenhowerTestModule],
        declarations: [CategorieTacheDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CategorieTacheDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CategorieTacheDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load categorieTache on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.categorieTache).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
