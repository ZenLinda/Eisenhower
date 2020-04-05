import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { TacheService } from 'app/entities/tache/tache.service';
import { ITache, Tache } from 'app/shared/model/tache.model';

describe('Service Tests', () => {
  describe('Tache Service', () => {
    let injector: TestBed;
    let service: TacheService;
    let httpMock: HttpTestingController;
    let elemDefault: ITache;
    let expectedResult: ITache | ITache[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TacheService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Tache(0, 'AAAAAAA', 'AAAAAAA', currentDate, currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateIdeale: currentDate.format(DATE_FORMAT),
            dateAlerte: currentDate.format(DATE_FORMAT),
            dateDelai: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Tache', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateIdeale: currentDate.format(DATE_FORMAT),
            dateAlerte: currentDate.format(DATE_FORMAT),
            dateDelai: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateIdeale: currentDate,
            dateAlerte: currentDate,
            dateDelai: currentDate
          },
          returnedFromService
        );

        service.create(new Tache()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Tache', () => {
        const returnedFromService = Object.assign(
          {
            libelle: 'BBBBBB',
            descritpion: 'BBBBBB',
            dateIdeale: currentDate.format(DATE_FORMAT),
            dateAlerte: currentDate.format(DATE_FORMAT),
            dateDelai: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateIdeale: currentDate,
            dateAlerte: currentDate,
            dateDelai: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Tache', () => {
        const returnedFromService = Object.assign(
          {
            libelle: 'BBBBBB',
            descritpion: 'BBBBBB',
            dateIdeale: currentDate.format(DATE_FORMAT),
            dateAlerte: currentDate.format(DATE_FORMAT),
            dateDelai: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateIdeale: currentDate,
            dateAlerte: currentDate,
            dateDelai: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Tache', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
