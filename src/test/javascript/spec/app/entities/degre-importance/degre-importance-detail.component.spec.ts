import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EisenhowerTestModule } from '../../../test.module';
import { DegreImportanceDetailComponent } from 'app/entities/degre-importance/degre-importance-detail.component';
import { DegreImportance } from 'app/shared/model/degre-importance.model';

describe('Component Tests', () => {
  describe('DegreImportance Management Detail Component', () => {
    let comp: DegreImportanceDetailComponent;
    let fixture: ComponentFixture<DegreImportanceDetailComponent>;
    const route = ({ data: of({ degreImportance: new DegreImportance(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EisenhowerTestModule],
        declarations: [DegreImportanceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DegreImportanceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DegreImportanceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load degreImportance on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.degreImportance).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
