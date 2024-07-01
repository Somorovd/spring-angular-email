import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

import { map } from 'rxjs';

import { AuthService } from '../../auth/services/auth.service';

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const authService = inject(AuthService);
  return authService.isAuthenticated().pipe(
    map((isAuthenticated: boolean) => {
      if (!isAuthenticated) {
        router.navigate(['/login']);
      }
      return isAuthenticated;
    })
  );
};
