import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { TimezoneService } from '../../../../services/timezone.service';
import { AccountRequest } from '../../../../types/api-output';

export enum ProcessAccountRequestPanelStatus {
  SUBMITTED,
  EDITING,
  APPROVED,
  REJECTED,
  SAVING,
}

/**
 * Panel to display an account request being processed.
 */
@Component({
  selector: 'tm-process-account-request-panel',
  templateUrl: './process-account-request-panel.component.html',
  styleUrls: ['./process-account-request-panel.component.scss'],
})
export class ProcessAccountRequestPanelComponent implements OnInit {

  backendErrorMessage : string = '';

  isFormSaving: boolean = false;


  @Input()
  accountRequest!: AccountRequest;

  @Input()
  panelStatus: ProcessAccountRequestPanelStatus = ProcessAccountRequestPanelStatus.SUBMITTED;

  @Output()
  editAccountRequestEvent: EventEmitter<void> = new EventEmitter();

  @Output()
  cancelEditAccountRequestEvent: EventEmitter<void> = new EventEmitter();

  @Output()
  saveAccountRequestEvent: EventEmitter<void> = new EventEmitter(); // TODO: determine event type

  @Output()
  approveAccountRequestEvent: EventEmitter<void> = new EventEmitter();

  @Output()
  rejectAccountRequestEvent: EventEmitter<void> = new EventEmitter();

  @Output()
  deleteAccountRequestEvent: EventEmitter<void> = new EventEmitter();

  @Output()
  resetAccountRequestEvent: EventEmitter<void> = new EventEmitter();

  // enum
  ProcessAccountRequestPanelStatus: typeof ProcessAccountRequestPanelStatus = ProcessAccountRequestPanelStatus;

  editedInstructorName!: string;
  editedInstructorInstitute!: string;
  editedInstructorEmail!: string;

  timezone: string = '';

  constructor(private timezoneService: TimezoneService) {
  }

  ngOnInit(): void {
    this.timezone = this.timezoneService.guessTimezone() || 'UTC';
  }

  editAccountRequest(): void {
    this.editAccountRequestEvent.emit();
    this.editedInstructorName = this.accountRequest!.name;
    this.editedInstructorInstitute = this.accountRequest!.institute;
    this.editedInstructorEmail = this.accountRequest!.email;
  }

  cancelEditAccountRequest(): void {
    this.cancelEditAccountRequestEvent.emit();
  }

  saveAccountRequest(): void {
    this.panelStatus = ProcessAccountRequestPanelStatus.SAVING;
    this.saveAccountRequestEvent.emit();
  }

  approveAccountRequest(): void {
    this.approveAccountRequestEvent.emit();
  }

  rejectAccountRequest(): void {
    this.rejectAccountRequestEvent.emit();
  }

  deleteAccountRequest(): void {
    this.deleteAccountRequestEvent.emit();
  }

  resetAccountRequest(): void {
    this.resetAccountRequestEvent.emit();
  }

}
