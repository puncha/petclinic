import {Component, Input, ViewChild} from "@angular/core";
import {Owner} from "../Owner";
import {NgbModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";
import _ = require("lodash");

@Component({
  moduleId: module.id,
  selector: 'ptc-owner-view-modal',
  templateUrl: 'owner-view-modal.component.html'
})
export class OwnerViewModalComponent {
  @ViewChild('modalTemplate') public modalTemplate;
  @Input() private owner: Owner;
  private modalTitle: string;

  constructor(private modalService: NgbModal) {
    this.modalTitle = "View Owner";
  }

  showModal() {
    let modalRef: NgbModalRef = this.modalService.open(this.modalTemplate);
    // to make zone happy, a reject function is needed
    modalRef.result.catch(_.noop);
  }
}