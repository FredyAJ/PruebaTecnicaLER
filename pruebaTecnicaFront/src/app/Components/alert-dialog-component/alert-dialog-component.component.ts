import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-alert-dialog-component',
  templateUrl: './alert-dialog-component.component.html',
  styleUrl: './alert-dialog-component.component.scss'
})
export class AlertDialogComponent {
  constructor(@Inject(MAT_DIALOG_DATA) public data: { message: string }) { }
}
