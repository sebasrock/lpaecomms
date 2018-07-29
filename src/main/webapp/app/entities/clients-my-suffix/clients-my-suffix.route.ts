import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ClientsMySuffix } from 'app/shared/model/clients-my-suffix.model';
import { ClientsMySuffixService } from './clients-my-suffix.service';
import { ClientsMySuffixComponent } from './clients-my-suffix.component';
import { ClientsMySuffixDetailComponent } from './clients-my-suffix-detail.component';
import { ClientsMySuffixUpdateComponent } from './clients-my-suffix-update.component';
import { ClientsMySuffixDeletePopupComponent } from './clients-my-suffix-delete-dialog.component';
import { IClientsMySuffix } from 'app/shared/model/clients-my-suffix.model';

@Injectable({ providedIn: 'root' })
export class ClientsMySuffixResolve implements Resolve<IClientsMySuffix> {
    constructor(private service: ClientsMySuffixService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((clients: HttpResponse<ClientsMySuffix>) => clients.body));
        }
        return of(new ClientsMySuffix());
    }
}

export const clientsRoute: Routes = [
    {
        path: 'clients-my-suffix',
        component: ClientsMySuffixComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'lpaecommsApp.clients.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'clients-my-suffix/:id/view',
        component: ClientsMySuffixDetailComponent,
        resolve: {
            clients: ClientsMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lpaecommsApp.clients.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'clients-my-suffix/new',
        component: ClientsMySuffixUpdateComponent,
        resolve: {
            clients: ClientsMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lpaecommsApp.clients.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'clients-my-suffix/:id/edit',
        component: ClientsMySuffixUpdateComponent,
        resolve: {
            clients: ClientsMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lpaecommsApp.clients.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const clientsPopupRoute: Routes = [
    {
        path: 'clients-my-suffix/:id/delete',
        component: ClientsMySuffixDeletePopupComponent,
        resolve: {
            clients: ClientsMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lpaecommsApp.clients.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
