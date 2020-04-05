import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EisenhowerTestModule } from '../../../test.module';
import { MatriceUpdateComponent } from 'app/entities/matrice/matrice-update.component';
import { MatriceService } from 'app/entities/matrice/matrice.service';
import { Matrice } from 'app/shared/model/matrice.model';

describe('Component Tests', () => {
  describe('Matrice Management Update Component', () => {
    let comp: MatriceUpdateComponent;
    let fixture: ComponentFixture<MatriceUpdateComponent>;
    let service: MatriceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EisenhowerTestModule],
        declarations: [MatriceUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MatriceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MatriceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MatriceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Matrice(123);
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
        const entity = new Matrice();
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
