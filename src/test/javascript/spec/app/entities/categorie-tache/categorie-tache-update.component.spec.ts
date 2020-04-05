import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EisenhowerTestModule } from '../../../test.module';
import { CategorieTacheUpdateComponent } from 'app/entities/categorie-tache/categorie-tache-update.component';
import { CategorieTacheService } from 'app/entities/categorie-tache/categorie-tache.service';
import { CategorieTache } from 'app/shared/model/categorie-tache.model';

describe('Component Tests', () => {
  describe('CategorieTache Management Update Component', () => {
    let comp: CategorieTacheUpdateComponent;
    let fixture: ComponentFixture<CategorieTacheUpdateComponent>;
    let service: CategorieTacheService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EisenhowerTestModule],
        declarations: [CategorieTacheUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CategorieTacheUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CategorieTacheUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CategorieTacheService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CategorieTache(123);
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
        const entity = new CategorieTache();
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
