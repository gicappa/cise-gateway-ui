import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICiseRule, defaultValue } from 'app/shared/model/cise-rule.model';

export const ACTION_TYPES = {
  SEARCH_CISERULES: 'ciseRule/SEARCH_CISERULES',
  FETCH_CISERULE_LIST: 'ciseRule/FETCH_CISERULE_LIST',
  FETCH_CISERULE: 'ciseRule/FETCH_CISERULE',
  CREATE_CISERULE: 'ciseRule/CREATE_CISERULE',
  UPDATE_CISERULE: 'ciseRule/UPDATE_CISERULE',
  DELETE_CISERULE: 'ciseRule/DELETE_CISERULE',
  RESET: 'ciseRule/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICiseRule>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type CiseRuleState = Readonly<typeof initialState>;

// Reducer

export default (state: CiseRuleState = initialState, action): CiseRuleState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_CISERULES):
    case REQUEST(ACTION_TYPES.FETCH_CISERULE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CISERULE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_CISERULE):
    case REQUEST(ACTION_TYPES.UPDATE_CISERULE):
    case REQUEST(ACTION_TYPES.DELETE_CISERULE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_CISERULES):
    case FAILURE(ACTION_TYPES.FETCH_CISERULE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CISERULE):
    case FAILURE(ACTION_TYPES.CREATE_CISERULE):
    case FAILURE(ACTION_TYPES.UPDATE_CISERULE):
    case FAILURE(ACTION_TYPES.DELETE_CISERULE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_CISERULES):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_CISERULE_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_CISERULE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_CISERULE):
    case SUCCESS(ACTION_TYPES.UPDATE_CISERULE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_CISERULE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/cise-rules';
const apiSearchUrl = 'api/_search/cise-rules';

// Actions

export const getSearchEntities: ICrudSearchAction<ICiseRule> = query => ({
  type: ACTION_TYPES.SEARCH_CISERULES,
  payload: axios.get<ICiseRule>(`${apiSearchUrl}?query=` + query)
});

export const getEntities: ICrudGetAllAction<ICiseRule> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CISERULE_LIST,
    payload: axios.get<ICiseRule>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ICiseRule> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CISERULE,
    payload: axios.get<ICiseRule>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ICiseRule> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CISERULE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICiseRule> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CISERULE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICiseRule> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CISERULE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
