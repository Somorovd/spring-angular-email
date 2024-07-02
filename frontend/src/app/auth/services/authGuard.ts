import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

import { Store } from '@ngrx/store';

import { filter, first, map } from 'rxjs';

import { selectCurrentUser } from '../store/reducers';
import { PersistanceService } from '../../shared/services/persistance.service';

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const persistanceService = inject(PersistanceService);
  const currentUser$ = inject(Store).select(selectCurrentUser);

  if (!persistanceService.get('userId')) {
    return router.createUrlTree(['/login']);
  }

  return currentUser$.pipe(
    filter((value) => value !== undefined),
    first(),
    map((currentUser) => {
      return currentUser ? true : router.createUrlTree(['/login']);
    })
  );
};
