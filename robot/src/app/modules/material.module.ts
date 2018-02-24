import {NgModule} from '@angular/core';
import {
  MatFormFieldModule, MatInputModule, MatPaginatorIntl, MatPaginatorModule, MatProgressSpinnerModule, MatSortModule,
  MatTableModule,
  MatToolbarModule
} from "@angular/material";
import {MatPolishPaginator} from "../components/polishPaginator";

@NgModule({
  imports: [
    MatToolbarModule,
    MatTableModule,
    MatFormFieldModule,
    MatPaginatorModule,
    MatSortModule,
    MatInputModule,
    MatProgressSpinnerModule
  ],
  exports: [
    MatToolbarModule,
    MatTableModule,
    MatFormFieldModule,
    MatPaginatorModule,
    MatSortModule,
    MatInputModule,
    MatProgressSpinnerModule
  ],
  providers: [{ provide: MatPaginatorIntl, useClass: MatPolishPaginator}]
})
export class MaterialModule {
}
