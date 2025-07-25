import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {RouterModule} from '@angular/router';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {AppComponent} from './app.component';
import {EnterSequenceComponent} from './enter-sequence/enter-sequence.component';
import {ResultListComponent} from './result-list/result-list.component';
import { Globals } from './globals';
import {LocalstorageService} from "./localstorage-service/localstorage.service";

@NgModule({
    imports: [
        BrowserModule,
        ReactiveFormsModule,
        RouterModule.forRoot([
            {path: '', component: ResultListComponent},
        ]),
        FormsModule,
        HttpClientModule,
    ],
    providers: [ Globals, LocalstorageService ],
    declarations: [
        AppComponent,
        EnterSequenceComponent,
        ResultListComponent,
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
