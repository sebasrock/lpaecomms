import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IClientsMySuffix } from 'app/shared/model/clients-my-suffix.model';
import { ClientsMySuffixService } from './clients-my-suffix.service';

@Component({
    selector: 'jhi-clients-my-suffix-delete-dialog',
    templateUrl: './clients-my-suffix-delete-dialog.component.html'
})
export class ClientsMySuffixDeleteDialogComponent {
    clients: IClientsMySuffix;

    constructor(
        private clientsService: ClientsMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.clientsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'clientsListModification',
                content: 'Deleted an clients'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-clients-my-suffix-delete-popup',
    template: ''
})
export class ClientsMySuffixDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ clients }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ClientsMySuffixDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.clients = clients;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
