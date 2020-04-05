import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EisenhowerTestModule } from '../../../test.module';
import { MatriceDetailComponent } from 'app/entities/matrice/matrice-detail.component';
import { Matrice } from 'app/shared/model/matrice.model';

describe('Component Tests', () => {
  describe('Matrice Management Detail Component', () => {
    let comp: MatriceDetailComponent;
    let fixture: ComponentFixture<MatriceDetailComponent>;
    const route = ({ data: of({ matrice: new Matrice(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EisenhowerTestModule],
        declarations: [MatriceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MatriceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MatriceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load matrice on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.matrice).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
