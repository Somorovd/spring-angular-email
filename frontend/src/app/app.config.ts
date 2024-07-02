import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';

import { provideState, provideStore } from '@ngrx/store';
import { provideStoreDevtools } from '@ngrx/store-devtools';
import { provideEffects } from '@ngrx/effects';
import { provideRouterStore, routerReducer } from '@ngrx/router-store';

import { environment } from '../environments/environment';

import { appRoutes } from './app.routes';

import { AuthEffects } from './auth/store/effects';
import { authFeatureKey, authReducer } from './auth/store/reducers';
import { MailEffects } from './shared/components/mail/store/effects';
import {
  mailFeatureKey,
  mailReducer,
} from './shared/components/mail/store/reducers';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(appRoutes),
    provideHttpClient(),
    provideStore({
      router: routerReducer,
    }),
    provideRouterStore(),
    provideState(authFeatureKey, authReducer),
    provideState(mailFeatureKey, mailReducer),
    provideEffects([AuthEffects, MailEffects]),
    provideStoreDevtools({
      maxAge: 25,
      logOnly: environment.production,
      autoPause: true,
    }),
  ],
};
