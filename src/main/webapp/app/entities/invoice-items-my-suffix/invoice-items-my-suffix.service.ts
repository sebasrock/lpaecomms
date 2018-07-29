import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IInvoiceItemsMySuffix } from 'app/shared/model/invoice-items-my-suffix.model';

type EntityResponseType = HttpResponse<IInvoiceItemsMySuffix>;
type EntityArrayResponseType = HttpResponse<IInvoiceItemsMySuffix[]>;

@Injectable({ providedIn: 'root' })
export class InvoiceItemsMySuffixService {
    private resourceUrl = SERVER_API_URL + 'api/invoice-items';

    constructor(private http: HttpClient) {}

    create(invoiceItems: IInvoiceItemsMySuffix): Observable<EntityResponseType> {
        return this.http.post<IInvoiceItemsMySuffix>(this.resourceUrl, invoiceItems, { observe: 'response' });
    }

    update(invoiceItems: IInvoiceItemsMySuffix): Observable<EntityResponseType> {
        return this.http.put<IInvoiceItemsMySuffix>(this.resourceUrl, invoiceItems, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IInvoiceItemsMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IInvoiceItemsMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
