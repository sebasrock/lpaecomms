import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IClientsMySuffix } from 'app/shared/model/clients-my-suffix.model';
import { ClientsMySuffixService } from './clients-my-suffix.service';

@Component({
    selector: 'jhi-clients-my-suffix-update',
    templateUrl: './clients-my-suffix-update.component.html'
})
export class ClientsMySuffixUpdateComponent implements OnInit {
    private _clients: IClientsMySuffix;
    isSaving: boolean;

    constructor(private clientsService: ClientsMySuffixService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ clients }) => {
            this.clients = clients;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.clients.id !== undefined) {
            this.subscribeToSaveResponse(this.clientsService.update(this.clients));
        } else {
            this.subscribeToSaveResponse(this.clientsService.create(this.clients));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IClientsMySuffix>>) {
        result.subscribe((res: HttpResponse<IClientsMySuffix>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get clients() {
        return this._clients;
    }

    set clients(clients: IClientsMySuffix) {
        this._clients = clients;
    }
}
