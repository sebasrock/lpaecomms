import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClientsMySuffix } from 'app/shared/model/clients-my-suffix.model';

@Component({
    selector: 'jhi-clients-my-suffix-detail',
    templateUrl: './clients-my-suffix-detail.component.html'
})
export class ClientsMySuffixDetailComponent implements OnInit {
    clients: IClientsMySuffix;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ clients }) => {
            this.clients = clients;
        });
    }

    previousState() {
        window.history.back();
    }
}
