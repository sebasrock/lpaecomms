/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LpaecommsTestModule } from '../../../test.module';
import { InvoiceItemsMySuffixDeleteDialogComponent } from 'app/entities/invoice-items-my-suffix/invoice-items-my-suffix-delete-dialog.component';
import { InvoiceItemsMySuffixService } from 'app/entities/invoice-items-my-suffix/invoice-items-my-suffix.service';

describe('Component Tests', () => {
    describe('InvoiceItemsMySuffix Management Delete Component', () => {
        let comp: InvoiceItemsMySuffixDeleteDialogComponent;
        let fixture: ComponentFixture<InvoiceItemsMySuffixDeleteDialogComponent>;
        let service: InvoiceItemsMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LpaecommsTestModule],
                declarations: [InvoiceItemsMySuffixDeleteDialogComponent]
            })
                .overrideTemplate(InvoiceItemsMySuffixDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(InvoiceItemsMySuffixDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InvoiceItemsMySuffixService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
