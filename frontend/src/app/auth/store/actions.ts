import { createActionGroup, props } from '@ngrx/store';
import { LoginRequestInterface } from '../types/loginRequest.interface';
import { UserInterface } from '../../shared/types/user.interface';
import { ErrorMap } from '../../shared/types/errorMap.type';

export const AuthActions = createActionGroup({
  source: 'Auth',
  events: {
    // ------------------------------------------------
    Login: props<{ request: LoginRequestInterface }>(),
    'Login Success': props<{ currentUser: UserInterface }>(),
    'Login Failed': props<{ errors: ErrorMap }>(),
    // ------------------------------------------------
  },
});
