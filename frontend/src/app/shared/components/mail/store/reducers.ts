import { createFeature, createReducer, createSelector, on } from '@ngrx/store';

import { MailStateInterface } from '../types/mailState.interface';

import { MailActions } from './actions';

const initialState: MailStateInterface = {
  inbox: {
    statuses: {},
    count: 0,
  },
  details: {
    status: null,
    emails: [],
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
    })),
    // ------------------------------------------------
    on(MailActions.getStatus, (state) => ({
      ...state,
      isLoading: true,
      error: null,
    })),
    on(MailActions.getStatusSuccess, (state, action) => ({
      ...state,
      details: {
        ...state.details,
        status: action.status,
      },
      isLoading: false,
    })),
    on(MailActions.getStatusFailed, (state, action) => ({
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
  selectDetails,
  selectIsLoading,
  selectError,
} = mailFeature;

export const selectDetailsStatus = createSelector(
  selectDetails,
  (details) => details.status
);

export const selectDetailsEmails = createSelector(
  selectDetails,
  (details) => details.emails
);
