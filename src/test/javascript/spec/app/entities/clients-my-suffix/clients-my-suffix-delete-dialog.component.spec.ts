/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LpaecommsTestModule } from '../../../test.module';
import { ClientsMySuffixDeleteDialogComponent } from 'app/entities/clients-my-suffix/clients-my-suffix-delete-dialog.component';
import { ClientsMySuffixService } from 'app/entities/clients-my-suffix/clients-my-suffix.service';

describe('Component Tests', () => {
    describe('ClientsMySuffix Management Delete Component', () => {
        let comp: ClientsMySuffixDeleteDialogComponent;
        let fixture: ComponentFixture<ClientsMySuffixDeleteDialogComponent>;
        let service: ClientsMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LpaecommsTestModule],
                declarations: [ClientsMySuffixDeleteDialogComponent]
            })
                .overrideTemplate(ClientsMySuffixDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ClientsMySuffixDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClientsMySuffixService);
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
