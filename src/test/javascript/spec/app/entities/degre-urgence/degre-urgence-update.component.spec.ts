import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EisenhowerTestModule } from '../../../test.module';
import { DegreUrgenceUpdateComponent } from 'app/entities/degre-urgence/degre-urgence-update.component';
import { DegreUrgenceService } from 'app/entities/degre-urgence/degre-urgence.service';
import { DegreUrgence } from 'app/shared/model/degre-urgence.model';

describe('Component Tests', () => {
  describe('DegreUrgence Management Update Component', () => {
    let comp: DegreUrgenceUpdateComponent;
    let fixture: ComponentFixture<DegreUrgenceUpdateComponent>;
    let service: DegreUrgenceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EisenhowerTestModule],
        declarations: [DegreUrgenceUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DegreUrgenceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DegreUrgenceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DegreUrgenceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DegreUrgence(123);
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
        const entity = new DegreUrgence();
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
