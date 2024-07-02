import { createFeature, createReducer, on } from '@ngrx/store';

import { AuthStateInterface } from '../types/authState.interface';

import { AuthActions } from './actions';

const initialState: AuthStateInterface = {
  isSubmitting: false,
  currentUser: undefined,
  errors: null,
  isLoading: false,
};

const authFeature = createFeature({
  name: 'auth',
  reducer: createReducer(
    initialState,
    // ------------------------------------------------
    on(AuthActions.login, (state) => ({
      ...state,
      isSubmitting: true,
      errors: null,
    })),
    on(AuthActions.loginSuccess, (state, action) => ({
      ...state,
      isSubmitting: false,
      currentUser: action.currentUser,
    })),
    on(AuthActions.loginFailed, (state, action) => ({
      ...state,
      isSubmitting: false,
      errors: action.errors,
    }))
    // ------------------------------------------------
  ),
});

export const {
  name: authFeatureKey,
  reducer: authReducer,
  selectCurrentUser,
  selectErrors,
  selectIsLoading,
  selectIsSubmitting,
} = authFeature;
