import { provideHttpClient, withFetch, withInterceptors } from '@angular/common/http';
import { APP_INITIALIZER, ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes';
import { authInterceptor } from './interceptors/auth.interceptor';
import { UserService } from './services/user.service';
import { startupServiceFactory } from './services/startupService';



//providers:[{provide: APP_INITIALIZER,useFactory:()=>{console.log("HI")},deps:[UserService],multi:true}],

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideHttpClient(withFetch(),withInterceptors([authInterceptor])),
    {
      provide: APP_INITIALIZER,useFactory:startupServiceFactory,deps:[UserService],multi:true
    },
  ]
};