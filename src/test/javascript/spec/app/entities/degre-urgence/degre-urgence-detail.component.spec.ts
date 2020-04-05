import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EisenhowerTestModule } from '../../../test.module';
import { DegreUrgenceDetailComponent } from 'app/entities/degre-urgence/degre-urgence-detail.component';
import { DegreUrgence } from 'app/shared/model/degre-urgence.model';

describe('Component Tests', () => {
  describe('DegreUrgence Management Detail Component', () => {
    let comp: DegreUrgenceDetailComponent;
    let fixture: ComponentFixture<DegreUrgenceDetailComponent>;
    const route = ({ data: of({ degreUrgence: new DegreUrgence(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EisenhowerTestModule],
        declarations: [DegreUrgenceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DegreUrgenceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DegreUrgenceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load degreUrgence on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.degreUrgence).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
