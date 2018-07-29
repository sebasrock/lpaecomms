import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { InvoicesMySuffix } from 'app/shared/model/invoices-my-suffix.model';
import { InvoicesMySuffixService } from './invoices-my-suffix.service';
import { InvoicesMySuffixComponent } from './invoices-my-suffix.component';
import { InvoicesMySuffixDetailComponent } from './invoices-my-suffix-detail.component';
import { InvoicesMySuffixUpdateComponent } from './invoices-my-suffix-update.component';
import { InvoicesMySuffixDeletePopupComponent } from './invoices-my-suffix-delete-dialog.component';
import { IInvoicesMySuffix } from 'app/shared/model/invoices-my-suffix.model';

@Injectable({ providedIn: 'root' })
export class InvoicesMySuffixResolve implements Resolve<IInvoicesMySuffix> {
    constructor(private service: InvoicesMySuffixService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((invoices: HttpResponse<InvoicesMySuffix>) => invoices.body));
        }
        return of(new InvoicesMySuffix());
    }
}

export const invoicesRoute: Routes = [
    {
        path: 'invoices-my-suffix',
        component: InvoicesMySuffixComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'lpaecommsApp.invoices.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'invoices-my-suffix/:id/view',
        component: InvoicesMySuffixDetailComponent,
        resolve: {
            invoices: InvoicesMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lpaecommsApp.invoices.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'invoices-my-suffix/new',
        component: InvoicesMySuffixUpdateComponent,
        resolve: {
            invoices: InvoicesMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lpaecommsApp.invoices.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'invoices-my-suffix/:id/edit',
        component: InvoicesMySuffixUpdateComponent,
        resolve: {
            invoices: InvoicesMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lpaecommsApp.invoices.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const invoicesPopupRoute: Routes = [
    {
        path: 'invoices-my-suffix/:id/delete',
        component: InvoicesMySuffixDeletePopupComponent,
        resolve: {
            invoices: InvoicesMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lpaecommsApp.invoices.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
