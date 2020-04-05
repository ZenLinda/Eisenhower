import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EisenhowerTestModule } from '../../../test.module';
import { DegreImportanceUpdateComponent } from 'app/entities/degre-importance/degre-importance-update.component';
import { DegreImportanceService } from 'app/entities/degre-importance/degre-importance.service';
import { DegreImportance } from 'app/shared/model/degre-importance.model';

describe('Component Tests', () => {
  describe('DegreImportance Management Update Component', () => {
    let comp: DegreImportanceUpdateComponent;
    let fixture: ComponentFixture<DegreImportanceUpdateComponent>;
    let service: DegreImportanceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EisenhowerTestModule],
        declarations: [DegreImportanceUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DegreImportanceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DegreImportanceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DegreImportanceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DegreImportance(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new DegreImportance();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
