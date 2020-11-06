import { NgModule } from '@angular/core';
import {MatIconModule} from '@angular/material/icon';
import {MatRadioModule} from '@angular/material/radio';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import { ReactiveFormsModule } from '@angular/forms';
import{ MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatCardModule} from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import {MatTooltipModule} from '@angular/material/tooltip';

const MaterialComponents=[
 
 MatIconModule,
 MatRadioModule,
 MatFormFieldModule,
 MatInputModule,
 ReactiveFormsModule,
 MatDatepickerModule,
 MatNativeDateModule,
 MatToolbarModule,
 MatButtonModule,
 MatCardModule,
 MatTooltipModule
 
];
@NgModule({
  imports: [MaterialComponents],
  exports: [MaterialComponents]
})
export class MaterialModule { }
