import { createFeature, createReducer, on } from '@ngrx/store';

import { MailStateInterface } from '../types/mailState.interface';

import { MailActions } from './actions';

const initialState: MailStateInterface = {
  inbox: {
    statuses: [],
    count: 0,
  },
  isLoading: false,
  error: null,
};

const mailFeature = createFeature({
  name: 'mail',
  reducer: createReducer(
    initialState,
    // ------------------------------------------------
    on(MailActions.getInbox, (state) => ({
      ...state,
      isLoading: true,
      error: null,
    })),
    on(MailActions.getInboxSuccess, (state, action) => ({
      ...state,
      isLoading: false,
      inbox: action.inbox,
    })),
    on(MailActions.getInboxFailed, (state, action) => ({
      ...state,
      isLoading: false,
    }))
    // ------------------------------------------------
  ),
});

export const {
  name: mailFeatureKey,
  reducer: mailReducer,
  selectInbox,
  selectIsLoading,
  selectError,
} = mailFeature;
