import { createActionGroup, props } from '@ngrx/store';

import { LoginRequestInterface } from '../types/loginRequest.interface';
import { Errors } from '../../shared/types/errors.interface';
import { UserInterface } from '../../shared/types/user.interface';

export const AuthActions = createActionGroup({
  source: 'Auth',
  events: {
    // ------------------------------------------------
    Login: props<{ request: LoginRequestInterface }>(),
    'Login Success': props<{ currentUser: UserInterface }>(),
    'Login Failed': props<{ errors: Errors }>(),
    // ------------------------------------------------
  },
});
