import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { EisenhowerTestModule } from '../../../test.module';
import { DegreImportanceComponent } from 'app/entities/degre-importance/degre-importance.component';
import { DegreImportanceService } from 'app/entities/degre-importance/degre-importance.service';
import { DegreImportance } from 'app/shared/model/degre-importance.model';

describe('Component Tests', () => {
  describe('DegreImportance Management Component', () => {
    let comp: DegreImportanceComponent;
    let fixture: ComponentFixture<DegreImportanceComponent>;
    let service: DegreImportanceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EisenhowerTestModule],
        declarations: [DegreImportanceComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: {
                subscribe: (fn: (value: Data) => void) =>
                  fn({
                    pagingParams: {
                      predicate: 'id',
                      reverse: false,
                      page: 0
                    }
                  })
              }
            }
          }
        ]
      })
        .overrideTemplate(DegreImportanceComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DegreImportanceComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DegreImportanceService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DegreImportance(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.degreImportances && comp.degreImportances[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DegreImportance(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.degreImportances && comp.degreImportances[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
