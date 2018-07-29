import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IClientsMySuffix } from 'app/shared/model/clients-my-suffix.model';

type EntityResponseType = HttpResponse<IClientsMySuffix>;
type EntityArrayResponseType = HttpResponse<IClientsMySuffix[]>;

@Injectable({ providedIn: 'root' })
export class ClientsMySuffixService {
    private resourceUrl = SERVER_API_URL + 'api/clients';

    constructor(private http: HttpClient) {}

    create(clients: IClientsMySuffix): Observable<EntityResponseType> {
        return this.http.post<IClientsMySuffix>(this.resourceUrl, clients, { observe: 'response' });
    }

    update(clients: IClientsMySuffix): Observable<EntityResponseType> {
        return this.http.put<IClientsMySuffix>(this.resourceUrl, clients, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IClientsMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IClientsMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
