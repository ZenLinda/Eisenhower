import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDegreImportance } from 'app/shared/model/degre-importance.model';

type EntityResponseType = HttpResponse<IDegreImportance>;
type EntityArrayResponseType = HttpResponse<IDegreImportance[]>;

@Injectable({ providedIn: 'root' })
export class DegreImportanceService {
  public resourceUrl = SERVER_API_URL + 'api/degre-importances';

  constructor(protected http: HttpClient) {}

  create(degreImportance: IDegreImportance): Observable<EntityResponseType> {
    return this.http.post<IDegreImportance>(this.resourceUrl, degreImportance, { observe: 'response' });
  }

  update(degreImportance: IDegreImportance): Observable<EntityResponseType> {
    return this.http.put<IDegreImportance>(this.resourceUrl, degreImportance, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDegreImportance>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDegreImportance[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
