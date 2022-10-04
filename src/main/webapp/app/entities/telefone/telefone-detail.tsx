import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './telefone.reducer';

export const TelefoneDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const telefoneEntity = useAppSelector(state => state.telefone.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="telefoneDetailsHeading">
          <Translate contentKey="escolaEbdApp.telefone.detail.title">Telefone</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="escolaEbdApp.telefone.id">Id</Translate>
            </span>
          </dt>
          <dd>{telefoneEntity.id}</dd>
          <dt>
            <span id="numero">
              <Translate contentKey="escolaEbdApp.telefone.numero">Numero</Translate>
            </span>
          </dt>
          <dd>{telefoneEntity.numero}</dd>
          <dt>
            <span id="whatsapp">
              <Translate contentKey="escolaEbdApp.telefone.whatsapp">Whatsapp</Translate>
            </span>
          </dt>
          <dd>{telefoneEntity.whatsapp ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="escolaEbdApp.telefone.pessoa">Pessoa</Translate>
          </dt>
          <dd>{telefoneEntity.pessoa ? telefoneEntity.pessoa.id : ''}</dd>
          <dt>
            <Translate contentKey="escolaEbdApp.telefone.responsavel">Responsavel</Translate>
          </dt>
          <dd>{telefoneEntity.responsavel ? telefoneEntity.responsavel.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/telefone" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/telefone/${telefoneEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TelefoneDetail;
